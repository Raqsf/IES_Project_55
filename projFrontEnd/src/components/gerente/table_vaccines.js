import React from 'react';
import Head from 'next/head';
import { Typography, Box, Switch, Toolbar, FormControlLabel, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TablePagination, TableSortLabel, Paper } from '@mui/material';
import PropTypes from 'prop-types';
import { visuallyHidden } from '@mui/utils';
import api from "../../api";

function createData(centro_vacinacao, n_vacinas_a_chegar, dia_chegada, n_vacinas_atual) {
  return { centro_vacinacao, n_vacinas_a_chegar, dia_chegada, n_vacinas_atual };
}

function descendingComparator(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

// This method is created for cross-browser compatibility, if you don't
// need to support IE11, you can use Array.prototype.sort() directly
function stableSort(array, comparator) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) {
      return order;
    }
    return a[1] - b[1];
  });
  return stabilizedThis.map((el) => el[0]);
}

function getComparator(order, orderBy) {
  return order === 'desc'
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

// Vacinas a chegar poderia ser o lote e qnd se clicava, via-se a info sobre ele
// const rows = [
  // createData('Centro de Vacinação de Aveiro', 159, '12/20/21', 24),
  // createData('Centro de Vacinação do Porto', 237, '12/22/21', 37),
  // createData('Centro de Vacinação de Lisboa', 262, '12/21/21', 24),
  // createData('Centro de Vacinação de Coimbra', 305, '12/20/21', 67),
  // createData('Centro de Vacinação de Setubal', 356, '12/21/21', 49),
  // createData('Centro de Vacinação de Faro', 236, '12/21/21', 59),
// ];

const headCells = [
  {
    id: 'centro_vacinacao',
    numeric: false,
    disablePadding: true,
    label: 'Centros de Vacinação',
  },
  {
    id: 'n_vacinas_a_chegar',
    numeric: true,
    disablePadding: false,
    label: 'Vacinas a chegar',
  },
  {
    id: 'dia_chegada',
    numeric: false,
    disablePadding: false,
    label: 'Data de Chegada',
  },
  {
    id: 'n_vacinas_atual',
    numeric: true,
    disablePadding: false,
    label: 'Número de Vacinas Atual',
  },
];

function EnhancedTableHead(props) {
  const { order, orderBy, rowCount, onRequestSort } =
    props;
  const createSortHandler = (property) => (event) => {
    onRequestSort(event, property);
  };

  return (
    <TableHead>
      <TableRow>
        {headCells.map((headCell) => (
          <TableCell
            key={headCell.id}
            align={headCell.numeric ? 'right' : 'left'}
            sortDirection={orderBy === headCell.id ? order : false}
          >
            <TableSortLabel
              active={orderBy === headCell.id}
              direction={orderBy === headCell.id ? order : 'asc'}
              onClick={createSortHandler(headCell.id)}
            >
              {headCell.label}
              {orderBy === headCell.id ? (
                <Box component="span" sx={visuallyHidden}>
                  {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                </Box>
              ) : null}
            </TableSortLabel>
          </TableCell>
        ))}
      </TableRow>
    </TableHead>
  );
}

EnhancedTableHead.propTypes = {
  onRequestSort: PropTypes.func.isRequired,
  order: PropTypes.oneOf(['asc', 'desc']).isRequired,
  orderBy: PropTypes.string.isRequired,
  rowCount: PropTypes.number.isRequired,
};

const EnhancedTableToolbar = () => {

  return (
    <Toolbar
      sx={{
        pl: { sm: 2 },
        pr: { xs: 1, sm: 1 },
      }}
    >
      <Typography
          sx={{ flex: '1 1 100%' }}
          variant="h6"
          id="tableTitle"
          component="div"
        >
          Vacinas
      </Typography>
    </Toolbar>
  );
};

const TableVaccines = (props) => { 
  // const {centros} = props;
  const [rows, setRows] = React.useState([]);
  const [order, setOrder] = React.useState('asc');
  const [orderBy, setOrderBy] = React.useState('calories');
  const [page, setPage] = React.useState(0);
  const [dense, setDense] = React.useState(false);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);

  const headers = {
    "Access-Control-Allow-Origin": "*",
    "Content-Type": "application/json",
  };

  React.useEffect(() => {
      api.get(
        `/lote`, headers
      ).then((response) => {
        console.log(response.data)
        let res = []
        for(let lote of response.data) {
          res.push({"centro_vacinacao": lote.centroVacinacao.nome, "n_vacinas_a_chegar": lote.quantidade, "dia_chegada":lote.data_chegada, "n_vacinas_atual": lote.centroVacinacao.capacidadeAtual})
        }
        setRows(res);
      })
      .catch((err) => {
        console.error("ops! ocorreu um erro" + err);
        alert("Erro");
      });
      const loop = setInterval(function() {
        api.get(
            `/lote`, headers
          ).then((response) => {
            let res = []
            for(let lote of response.data) {
              res.push({"centro_vacinacao": lote.centroVacinacao.nome, "n_vacinas_a_chegar": lote.quantidade, "dia_chegada":lote.data_chegada, "n_vacinas_atual": lote.centroVacinacao.capacidadeAtual})
            }
            setRows(res);
          })
          .catch((err) => {
            console.error("ops! ocorreu um erro" + err);
            alert("Erro");
          }
        );
        }, 1000);
        return () => clearInterval(loop);
  }, []);

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === 'asc';
    setOrder(isAsc ? 'desc' : 'asc');
    setOrderBy(property);
  };

  const handleClick = (event, name) => {
    // const selectedIndex = selected.indexOf(name);
    // let newSelected = [];

    // if (selectedIndex === -1) {
    //   newSelected = newSelected.concat(selected, name);
    // } else if (selectedIndex === 0) {
    //   newSelected = newSelected.concat(selected.slice(1));
    // } else if (selectedIndex === selected.length - 1) {
    //   newSelected = newSelected.concat(selected.slice(0, -1));
    // } else if (selectedIndex > 0) {
    //   newSelected = newSelected.concat(
    //     selected.slice(0, selectedIndex),
    //     selected.slice(selectedIndex + 1),
    //   );
    // }

    // setSelected(newSelected);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleChangeDense = (event) => {
    setDense(event.target.checked);
  };

  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - rows.length) : 0;


  return (
  <>
    <Paper 
     sx={{ width: '100%', mb: 2 }}
     elevation={3} 
    >
      <EnhancedTableToolbar />
      <TableContainer>
        <Table
          sx={{ minWidth: 750 }}
          aria-labelledby="tableTitle"
          size={dense ? 'small' : 'medium'}
        >
          <EnhancedTableHead
            order={order}
            orderBy={orderBy}
            onRequestSort={handleRequestSort}
            rowCount={rows.length}
          />
          <TableBody>
            {/* if you don't need to support IE11, you can replace the `stableSort` call with:
              rows.slice().sort(getComparator(order, orderBy)) */}
            {stableSort(rows, getComparator(order, orderBy))
              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((row, index) => {

                return (
                  <TableRow
                    hover
                    onClick={(event) => handleClick(event, row.centro_vacinacao)}
                    tabIndex={-1}
                    key={index}
                  >
                    <TableCell
                      component="th"
                      scope="row"
                    >
                      {row.centro_vacinacao}
                    </TableCell>
                    <TableCell align="right">{row.n_vacinas_a_chegar}</TableCell>
                    <TableCell align="left">{row.dia_chegada}</TableCell>
                    <TableCell align="right">{row.n_vacinas_atual}</TableCell>
                  </TableRow>
                );
              })}
            {emptyRows > 0 && (
              <TableRow
                style={{
                  height: (dense ? 33 : 53) * emptyRows,
                }}
              >
                <TableCell colSpan={6} />
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[5, 10, 25]}
        component="div"
        count={rows.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </Paper>
    <FormControlLabel
      control={<Switch checked={dense} onChange={handleChangeDense} />}
      label="Dense padding"
    />
  </>
  );
}


export default TableVaccines;
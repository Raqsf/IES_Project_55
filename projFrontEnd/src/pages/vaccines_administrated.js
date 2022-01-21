import { useEffect } from 'react';
import Head from 'next/head';
import { Typography, Container, Box, Divider, Grid, TextField, Button, Avatar,
	Card,
	Table,
	TableBody,
	TableCell,
	TableHead,
	TablePagination,
	TableRow,
	TableSortLabel } from '@mui/material';
import PerfectScrollbar from "react-perfect-scrollbar";
import { DashboardLayoutGerente } from '../components/dashboard-layout-gerente';
//import { customers } from '../__mocks__/customers';
import { useRouter } from "next/router";
import api from "../api";
// import { useParams } from "react-router-dom";
import { useState } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

toast.configure()
const PeopleVaccinated = () => {
    const router = useRouter();
    const [loading, setLoading] = useState(true);
    const [loadingData, setLoadingData] = useState(true);
    const [centro, setCentro] = useState();
    const [utentes, setUtentes] = useState([]);
    const [page, setPage] = useState(0);
	  const [size, setSize] = useState(10);
	  const [count, setCount] = useState(0);

	  const handleLimitChange = (event) => {
	  	setSize(event.target.value);
	  };
	  const handlePageChange = (event, newPage) => {
	  	setPage(newPage);
	  };

    const {
        query: { id, nome },
    } = router
    console.log(id)

    if(id) {
      localStorage.setItem("id_people_vaccinated_info", id);
    }
    
    if(nome) {
      localStorage.setItem("vaccination_center_name1", nome);
    }

    const headers = {
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json",
    };    

    useEffect(() => {
      // setLoadingData(true);
      // console.log( `/vacinacao/utente_vacinados/${id}`)
      // if(id) {
        // api.get(
          // `/vacinacao/utente_vacinados/${id}`, headers
        // ).then((response) => {
          // setUtentes(response.data);
          // console.log(response.data)
          // setLoadingData(false);
        // })
        // .catch((err) => {
          // console.error("ops! ocorreu um erro" + err);
          // alert("Erro");
        // })
      // }
      // const loop = setInterval(function() {
        // id = localStorage.getItem("id_people_vaccinated_info");
        // api.get(
          // `/vacinacao/utente_vacinados/${id}`, headers
        // ).then((response) => {
          // setUtentes(response.data);
          // console.log(response.data)
          // setLoadingData(false);
        // })
        // .catch((err) => {
          // console.error("ops! ocorreu um erro" + err);
          // alert("Erro");
        // })
      // }, 1000);
      // return () => clearInterval(loop);
    }, []);
    
    return (
  <>
    <Head>
      <title>
        Vacinas Administradas | Vaccination Desk
      </title>
    </Head>
    <Box
      component="main"
      sx={{
        flexGrow: 1,
        py: 8
      }}
    >
      <Container maxWidth={false}>
        <Typography
          sx={{ m: 1 }}
          variant="h4"
        >
          Vacinas Administradas
        </Typography>
        <Typography
          sx={{ m: 1 }}
          variant="string"
        >
          {nome}
        </Typography>

        <Card sx={{ mt: 2}}>
		    	<PerfectScrollbar>
		    		<Box minWidth={1050}>
		    			<Table>
		    				<TableHead>
		    					<TableRow>
		    						<TableCell>Vacina</TableCell>
		    						<TableCell>Lote</TableCell>
		    						<TableCell>
		    							<TableSortLabel active direction="desc">
		    								Data Validade
		    							</TableSortLabel>
		    						</TableCell>
		    						<TableCell>Utente</TableCell>
		    					</TableRow>
		    				</TableHead>
		    				<TableBody>
		    					{/* {utentes.map((utente) => ( */}
		    						<TableRow
		    							hover
		    							// key={utente.n_utente}
		    						>
                      <TableCell>
                      	<Box alignItems="center" display="flex">
                      		<Avatar /* className={classes.avatar} */>
                      			{/* {getInitials( */}
                      				{/* transaction.transaction */}
                      					{/* .client.name */}
                      			{/* )} */}R
                      		</Avatar>
                      		<Typography
                      			color="textPrimary"
                      			variant="body1"
                      		>
                      			{/* { */}
                      				{/* transaction.transaction */}
                      					{/* .client.name */}
                      			{/* } */}S
                      		</Typography>
                      	</Box>
                      </TableCell>
		    							<TableCell>
		    								{/* {transaction.total.toFixed(2)}€ */}Q
		    							</TableCell>
		    							<TableCell>
		    								{/* {transaction.products.length} */}W
		    							</TableCell>
		    							<TableCell>
		    								{/* {moment( */}
		    									{/* transaction.transaction.date */}
		    								{/* ).format("DD/MM/YYYY, HH:mm:ss")} */}E
		    							</TableCell>
		    						</TableRow>
		    					{/* ))} */}
		    				</TableBody>
		    			</Table>
		    		</Box>
		    	</PerfectScrollbar>
		    	<TablePagination
		    		component="div"
		    		count={count}
		    		onChangePage={handlePageChange}
		    		onChangeRowsPerPage={handleLimitChange}
		    		page={page}
		    		rowsPerPage={size}
		    		rowsPerPageOptions={[5, 10, 25]}
		    	/>
		    </Card>
      </Container>
    </Box>
  </>
);
    }

PeopleVaccinated.getLayout = (page) => (
  <DashboardLayoutGerente>
    {page}
  </DashboardLayoutGerente>
);

export default PeopleVaccinated;
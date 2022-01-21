import { useEffect } from 'react';
import Head from 'next/head';
import { Typography, Container, Box, Avatar,
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
const VaccinesAdministered = () => {
    const router = useRouter();
    const [loading, setLoading] = useState(true);
    const [loadingData, setLoadingData] = useState(true);
    const [vacinas, setVacinas] = useState([]);
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
      // console.log( `/vacinacao/vacinas_administradas_hoje/${id}`)
      // if(id) {
        // api.get(
          // `/vacinacao/vacinas_administradas_hoje/${id}`, headers
        // ).then((response) => {
           // if(response.data.length > 0) {
               // setVacinas(response.data);
            // } else {
                // setVacinas([]);
            // }
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
          // `/vacinacao/vacinas_administradas_hoje/${id}`, headers
        // ).then((response) => {
           // if(response.data.length > 0) {
              // setVacinas(response.data);
           // } else {
               // setVacinas([]);
           // }
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
		    					{/* {vacinas.length > 0 ? vacinas.map((vacina) => ( */}
		    						<TableRow
		    							hover
		    							// key={vacina.n_utente}
		    						>
                      <TableCell>
                      	<Box alignItems="center" display="flex">
                      		<Avatar /* className={classes.avatar} */>
                      			{/* {getInitials( */}
                      				{/* transaction.transaction */}
                      					{/* .client.name */}
                      			{/* )} */}
                      		</Avatar>
                      		<Typography
                      			color="textPrimary"
                      			variant="body1"
                      		>
                      			{/* { */}
                      				{/* transaction.transaction */}
                      					{/* .client.name */}
                      			{/* } */}
                            {/* {vacina.nome_vacina} */}
                      		</Typography>
                      	</Box>
                      </TableCell>
		    							<TableCell>
		    								{/* {transaction.total.toFixed(2)}€ */}Q
                        {/* {vacina.lote} */}
		    							</TableCell>
                      <TableCell>
                      	{/* {moment( */}
                      		{/* vacina.data_validade */}
                      	{/* ).format("DD/MM/YYYY, HH:mm:ss")} */}E
                      </TableCell>
		    							<TableCell>
		    								{/* {transaction.products.length} */}W
                        {/* {vacina.n_utente} */}
		    							</TableCell>
		    						</TableRow>
		    					{/* )) : null} */}
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

VaccinesAdministered.getLayout = (page) => (
  <DashboardLayoutGerente>
    {page}
  </DashboardLayoutGerente>
);

export default VaccinesAdministered;
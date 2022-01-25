import { useEffect } from 'react';
import Head from 'next/head';
import { Typography, Container, Box, Avatar, Card, Table, TableBody, TableCell, TableHead, TablePagination, TableRow, TableSortLabel } from '@mui/material';
import PerfectScrollbar from "react-perfect-scrollbar";
import { DashboardLayoutGerente } from '../components/dashboard-layout-gerente';
//import { customers } from '../__mocks__/customers';
import { useRouter } from "next/router";
import api from "../api";
import { useState } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

toast.configure()
const PeopleVaccinated = () => {
    const router = useRouter();
    const [loading, setLoading] = useState(true);
    // const [loadingData, setLoadingData] = useState(true);
    const [rows, setRows] = useState([]);    
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    
    useEffect(() => { 
      setLoading(true);
      if(!JSON.parse(localStorage.getItem("login"))) {
        router.push("/");
      } else {
        setLoading(false);
      }
    })
    

    const handleChangePage = (event, newPage) => {
      setPage(newPage);
    };
    const handleChangeRowsPerPage = (event) => {
      setRowsPerPage(parseInt(event.target.value, 10));
      setPage(0);
    };

    let {
        query: { id, nome },
    } = router
    console.log(id, nome)

    if(id) {
      localStorage.setItem("id_people_vaccinated_info", id);
    }

    if(nome) {
      localStorage.setItem("vaccination_center_name", nome);
    }
  

    const headers = {
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json",
    };    

    useEffect(() => {
      // setLoadingData(true);
      if(id) {
        api.get(
          `/vacinacao/utente_vacinados/${id}`, headers
        ).then((response) => {
          if(response.data.length > 0) {
            setRows(response.data);
          } else {
            setRows([]);
          }
          console.log(Object.values(response.data))
          // setLoadingData(false);
        })
        .catch(function (error) {
          if (error.response) {
            toast.error(error.response.data.message, {position: toast.POSITION.TOP_CENTER}.data.message, {position: toast.POSITION.TOP_CENTER});
          } else if (error.request) {
            console.log(error.request);
          } else {
            console.log('Error', error.message);
          }
          console.log(error.config);
        });
      }
      const loop = setInterval(function() {
        id = localStorage.getItem("id_people_vaccinated_info");
        api.get(
          `/vacinacao/utente_vacinados/${id}`, headers
        ).then((response) => {
          if(response.data.length > 0) {
            setRows(response.data);
          } else {
            setRows([]);
          }
          console.log(response.data)
          // setLoadingData(false);
        })
        .catch(function (error) {
          if (error.response) {
            toast.error(error.response.data.message, {position: toast.POSITION.TOP_CENTER});
          } else if (error.request) {
            console.log(error.request);
          } else {
            console.log('Error', error.message);
          }
          console.log(error.config);
        });
      }, 1000);
      return () => clearInterval(loop);
    }, []);
    // 
    // if(id) {
    //   localStorage.setItem("id", id);
    // }
    //  
    // const [centro, setCentro] = React.useState('');

    // 
    // React.useEffect(() => {
    //   setLoading(true);
    //   if (id) {
        // api.get(
            // `/centrovacinacao/${id}`, headers
        //   ).then((response) => {
            // setCentro(response.data);
            // setCapacity(response.data.capacidadeMax);
            // setLoading(false);
        //   })
        //   .catch((err) => {
            // console.error("ops! ocorreu um erro" + err);
            // alert("Erro");
            // if(response.status === 500 && typeof id == undefined) {
            //   alert("Erro")
            // }
        //   })
        // }
    //   const loop = setInterval(function() {

        // id = localStorage.getItem("id");

        // api.get(
            // `/centrovacinacao/${id}`, headers
        //   ).then((response) => {
            // setCentro(response.data);
            // setCapacity(response.data.capacidadeMax);
        //   })
        //   .catch((err) => {
            // console.error("ops! ocorreu um erro" + err);
            // alert("Erro");
        //   }
        // );
        // }, 1000);
        // return () => clearInterval(loop);
    //   }, []);

    return (
  <>
    <Head>
      <title>
        Pessoas Vacinadas | Vaccination Desk
      </title>
    </Head>
    {!loading ? 
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
          Pessoas Vacinadas
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
		    						<TableCell>Utente</TableCell>
		    						<TableCell>Número</TableCell>
		    						<TableCell>
		    							<TableSortLabel active >
		    								Email
		    							</TableSortLabel>
		    						</TableCell>
		    					</TableRow>
		    				</TableHead>
		    				<TableBody>
		    					{rows.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((utente) => (
		    						<TableRow
		    							hover
		    							key={utente.id}
		    						>
                      <TableCell>
                      	<Box alignItems="center" display="flex">
                      		
                      		<Typography
                      			color="textPrimary"
                      			variant="body1"
                      		>
                      			{/* { */}
                      				{/* transaction.transaction */}
                      					{/* .client.name */}
                      			{/* } */}
                            {utente.nome}
                      		</Typography>
                      	</Box>
                      </TableCell>
                      <TableCell>
                      	{/* {transaction.total.toFixed(2)}€ */}
                        {utente.id}
                      </TableCell>
                      
		    							<TableCell>
		    								{/* {transaction.total.toFixed(2)}€ */}
                        {utente.email}
		    							</TableCell>
		    						</TableRow>
		    					))} 
		    				</TableBody>
		    			</Table>
		    		</Box>
		    	</PerfectScrollbar>
		    	<TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={rows.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
		    	/>
		    </Card>
      </Container>
    </Box>
    : null}
  </>
);
    }

if (typeof window !== 'undefined') {
  if(JSON.parse(localStorage.getItem("login"))) {
    PeopleVaccinated.getLayout = (page) => (
      <DashboardLayoutGerente>
        {page}
      </DashboardLayoutGerente>
    );
  } else {
    window.location.href = "/";
  }
}

export default PeopleVaccinated;
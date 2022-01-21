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
const PeopleVaccinated = () => {
    const router = useRouter();
    const [loading, setLoading] = useState(true);
    const [loadingData, setLoadingData] = useState(true);
    // const [centro, setCentro] = useState();
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
      setLoadingData(true);
      console.log( `/vacinacao/utente_vacinados/${id}`)
      if(id) {
        api.get(
          `/vacinacao/utente_vacinados/${id}`, headers
        ).then((response) => {
          if(response.data.length > 0) {
            setUtentes(response.data);
          }
          setUtentes([]);
          console.log(response.data)
          setLoadingData(false);
        })
        .catch((err) => {
          console.error("ops! ocorreu um erro" + err);
          alert("Erro");
        })
      }
      const loop = setInterval(function() {
        id = localStorage.getItem("id_people_vaccinated_info");
        api.get(
          `/vacinacao/utente_vacinados/${id}`, headers
        ).then((response) => {
          if(response.data.length > 0) {
            setUtentes(response.data);
          }
          setUtentes([]);
          console.log(response.data)
          setLoadingData(false);
        })
        .catch((err) => {
          console.error("ops! ocorreu um erro" + err);
          alert("Erro");
        })
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
		    							<TableSortLabel active direction="desc">
		    								Data Nascimento
		    							</TableSortLabel>
		    						</TableCell>
		    						<TableCell>Email</TableCell>
		    					</TableRow>
		    				</TableHead>
		    				<TableBody>
		    					{utentes.length > 0 ? utentes.map((utente) => (
		    						<TableRow
		    							hover
		    							key={utente.n_utente}
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
                            {utente.nome}
                      		</Typography>
                      	</Box>
                      </TableCell>
                      <TableCell>
                      	{/* {transaction.total.toFixed(2)}€ */}
                        {utente.n_utente}
                      </TableCell>
                      <TableCell>
                      	{moment(
                      		utente.data_nascimento
                      	).format("DD/MM/YYYY")}
                      </TableCell>
		    							<TableCell>
		    								{/* {transaction.total.toFixed(2)}€ */}
                        {utente.email}
		    							</TableCell>
		    						</TableRow>
		    					)) : null} 
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
import { useEffect, useState } from 'react';
import { List, ListSubheader, ListItemText, Typography, LinearProgress } from '@mui/material';
//import { customers } from '../__mocks__/customers';
import api from "../../api";
import { useRouter } from 'next/router';

export const People = () => {
    const [people, setPeople] = useState([]);
    const [loading, setLoading] = useState(true);
    const headers = {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
    };

    const { query } = useRouter();
    let id = query.id

    if(id) {
      localStorage.setItem("id_people", id);
    }
      
      useEffect(() => {
        setLoading(true);
        if (id) {
          api.get(
              `/vacinacao/real_time/` + id , headers
            ).then((response) => {
            //   setCentro(response.data);
            console.log("oq veio do SP-> " + response.data)
            setPeople(response.data)
            setLoading(false);
            })
            .catch((err) => {
              console.error("ops! ocorreu um erro" + err);
              alert("Erro");
            })
        }
        const loop = setInterval(function() {
            id = localStorage.getItem("id_people");
            api.get(`/vacinacao/real_time/` + id , headers
              ).then((response) => {
                // setCentro(response.data);
                // console.log(response.data)
                setPeople(response.data)
                setLoading(false);
              });
          }, 1000);
          return () => clearInterval(loop);
      }, []);
  
    return(
        <>
        {!loading && people.length > 0 ? 
        <List
            sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper', pt: 2 }}
            component="nav"
            aria-labelledby="nested-list-subheader"
            subheader={
                <ListSubheader component="div" id="nested-list-subheader">
                Pessoas A Serem Vacinadas
                </ListSubheader>
            }
            >
                {people.map((person) => (
                    <ListItemText primary={person} key={person}/>
                ))}
        </List> 
        : !loading && people.length === 0 ? 
        <Typography
            sx={{ m: 1 }}
            variant="string"
        >
            Não existem pessoas no centro
        </Typography>
        : <LinearProgress />
        }
    </>
    );
}
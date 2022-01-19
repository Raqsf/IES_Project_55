import {
    Box,
    Card,
    CardContent,
    Divider,
    Typography
  } from '@mui/material';
import { useRouter } from 'next/router';

 
  const user = {
    scheduled: true,
    date: '10/10/2021 11:13',
    name: 'Catarina Silva',
    number: '123456789',
    place: 'Centro Vacinação Aveiro'
  };

  
  export const Info = () => {
    const router = useRouter();
    const {
        query: { utente_nome, utente_num, centro, morada, data }
    } = router
    console.log(utente_nome, utente_num, centro, morada, data)

  return (
    <Card>
      <CardContent>
        <Box
          sx={{
            alignItems: 'center',
            display: 'flex',
            flexDirection: 'column'
          }}
        >
          <Typography
            color="textPrimary"
            gutterBottom
            variant="h5"
          >
            UTENTE
          </Typography>
          <Typography
            color="textSecondary"
            variant="subtitle2"
          >
            {utente_nome}
          </Typography>
          <Typography
            color="textSecondary"
            variant="subtitle2"
          >
            {utente_num}
          </Typography>
        </Box>
      </CardContent>
      <Divider />
      <CardContent>
        {user.scheduled ? 
          <Box
            sx={{
                alignItems: 'center',
                display: 'flex',
                flexDirection: 'column'
            }}
            >
              <Typography
                  color="textPrimary"
                  gutterBottom
                  variant="h5"
              >
                  Estado: Agendado
              </Typography>
              <Typography
                  color="textSecondary"
                  variant="subtitle2"
              >
                  {data}
              </Typography>
              <Typography
                  color="textSecondary"
                  variant="subtitle2"
              >
                  {centro}
              </Typography>
              <Typography
                  color="textSecondary"
                  variant="subtitle2"
              >
                  {morada}
              </Typography>
          </Box>
          : 
          <Box
            sx={{
                alignItems: 'center',
                display: 'flex',
                flexDirection: 'column'
            }}
          >
              <Typography
                  color="textPrimary"
                  gutterBottom
                  variant="h5"
              >
                  Estado: Não Agendado
              </Typography>
          </Box>
        }
      </CardContent>
    </Card>
  );
}
  
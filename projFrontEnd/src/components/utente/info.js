import {
    Box,
    Card,
    CardContent,
    Divider,
    Typography
  } from '@mui/material';
  
  const user = {
    scheduled: true,
    date: '10/10/2021 11:13',
    name: 'Catarina Silva',
    number: '123456789',
    place: 'Centro Vacinação Aveiro'
  };


    let state;
    if (user.scheduled) {
        state = (
            <>
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
                    {user.date}
                </Typography>
                <Typography
                    color="textSecondary"
                    variant="subtitle2"
                >
                    {user.place}
                </Typography>
            </Box>
            </>
        );
    } else {
        state = (
            <>
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
            </>
        ); 
    }    
  
  export const Info = (props) => (
    <Card {...props}>
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
            {user.name}
          </Typography>
          <Typography
            color="textSecondary"
            variant="subtitle2"
          >
            {user.number}
          </Typography>
        </Box>
      </CardContent>
      <Divider />
      <CardContent>
        {state}
      </CardContent>
    </Card>
  );
  
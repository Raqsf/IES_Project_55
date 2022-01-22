import {
    Box,
    Card,
    CardContent,
    Divider,
    Typography
  } from '@mui/material';
import { useRouter } from 'next/router';
 
  export const Info = () => {
    const router = useRouter();
    const {
        query: { utente_nome, utente_num, estado, centro, morada, data }
    } = router
    const d = new Date(data)
    const data_str = d.toLocaleDateString() + " " + d.toLocaleDateString();
    // if(data) {
      // data = new Date(data);
    // }

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
                  Estado: {estado}
              </Typography>
              
              <Typography
                  color="textSecondary"
                  variant="subtitle2"
              >
                  {data ? data_str : null}
                  {/* {data.toLocaleDateString()} {data.toLocaleTimeString()} */}
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
      </CardContent>
    </Card>
  );
}
  
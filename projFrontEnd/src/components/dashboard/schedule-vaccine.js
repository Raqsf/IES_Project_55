import { Avatar, Box, Card, CardContent, Grid, Typography, Button } from '@mui/material';
import HealthAndSafetyIcon from '@mui/icons-material/HealthAndSafety';
import NextLink from 'next/link';

export const Schedule = (props) => (
  <Card {...props}>
    <CardContent>
      <Grid
        container
        spacing={3}
        sx={{ justifyContent: 'space-between' }}
      >
        <Grid item
            lg={10}
            sm={10}
            xl={10}
            xs={10}
        >
          <Typography
            color="textPrimary"
            variant="h5"
          >
            Agende a sua Vacina
          </Typography>
        </Grid>
        <Grid item
            lg={2}
            sm={2}
            xl={2}
            xs={2}
            >
          <Avatar
            sx={{
              backgroundColor: 'error.main',
              height: 56,
              width: 56
            }}
          >
            <HealthAndSafetyIcon />
          </Avatar>
        </Grid>
      </Grid>
      <Box
        sx={{
          pt: 2,
          display: 'flex',
          alignItems: 'center'
        }}
      >
        <Typography
            color="textSecondary"
            gutterBottom
            variant="body2"
        >
            Depois de efetuar o pedido de agendamento é realizada uma validação para confirmar se reúne os critérios de elegibilidade em vigor, para a toma da vacina. 
            Irá receber um código QR por email juntamente com a indicação da hora e local onde se realizará a vacinação. 
            Terá de apresentar o código QR quando for levar a dose.
        </Typography>
        {/* <ArrowDownwardIcon color="error" />
        <Typography
          color="error"
          sx={{
            mr: 1
          }}
          variant="body2"
        >
          12%
        </Typography>
        <Typography
          color="textSecondary"
          variant="caption"
        >
          Since last month
        </Typography> */}
      </Box>
      <Box sx={{
          pt: 2,
          display: 'flex',
          alignItems: 'center'
        }}
      >
        <NextLink href="/agendamento" passHref>
          <Button
            color="secondary"
            variant="contained"
            sx={{ mr: 1 }}
          >
            Agendar
          </Button>
        </NextLink>
      </Box>
    </CardContent>
  </Card>
);

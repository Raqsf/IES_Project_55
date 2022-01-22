import { Avatar, Box, Card, CardContent, Grid, Typography, Button } from '@mui/material';
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
import NextLink from 'next/link';

export const ManageVaccines = (props) => (
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
            Gerir Vacinas
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
            <ManageAccountsIcon />
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
          Gira todo o processo de vacinação, marque agendamentos por ordem de inscrição, ou por critérios de prioridade como idade, ou certas patologias.
          Em cada centro de vacinação é possivel saber que pessoas é que se estão a vacinar em tempo real, bem como ver todas as que já se vacinaram até ao momento.
          É possivel ainda, saber as quantidades de vacinas que cada centro tem, e as quantidades que estão previstas receber nos proximos dias.
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
        <NextLink href="/vaccines" passHref>
          <Button
            color="secondary"
            variant="contained"
            sx={{ mr: 1 }}
            // href="/agendamento"
          >
            Gerir
          </Button>
        </NextLink>
      </Box>
    </CardContent>
  </Card>
);

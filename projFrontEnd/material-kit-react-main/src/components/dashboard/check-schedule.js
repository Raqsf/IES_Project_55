import { Avatar, Box, Card, CardContent, Grid, Typography, Button } from '@mui/material';
import CalendarTodayIcon from '@mui/icons-material/CalendarToday';
import PermContactCalendarIcon from '@mui/icons-material/PermContactCalendar';

export const Check = (props) => (
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
            Verifique o estado do agendamento
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
              backgroundColor: 'success.main',
              height: 56,
              width: 56
            }}
          >
            <PermContactCalendarIcon />
          </Avatar>
        </Grid>
      </Grid>
      <Box
        sx={{
          alignItems: 'center',
          display: 'flex',
          pt: 2
        }}
      >
        <Typography
            color="textSecondary"
            gutterBottom
            variant="body2"
        >
            Consulte a data e o local da vacinação, caso já tenha sido agendada. 
            Caso contrário verifique em que estado se encontra.
        </Typography>
      </Box>
      <Box sx={{
          pt: 2,
          display: 'flex',
          alignItems: 'center'
        }}
      >
        <Button
          color="secondary"
          variant="contained"
          sx={{ mr: 1 }}
        >
          Verificar
        </Button>
      </Box>
    </CardContent>
  </Card>
);

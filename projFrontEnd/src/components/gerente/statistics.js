import { Avatar, Box, Card, CardContent, Grid, Typography, Button } from '@mui/material';
//import CalendarTodayIcon from '@mui/icons-material/CalendarToday';
import AnalyticsIcon from '@mui/icons-material/Analytics';
import NextLink from 'next/link';

export const Statistics = (props) => (
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
            Estatísticas
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
            <AnalyticsIcon />
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
          Consulte todas as informações com base em estatísticas sobre todo o processo de vacinação.
          
        </Typography>
      </Box>
      <Box sx={{
        pt: 13,
        display: 'flex',
        alignItems: 'center'
      }}
      >
        <NextLink href="/statistics" passHref>
          <Button
            color="secondary"
            variant="contained"
            sx={{ mr: 1 }}
          >
            Verificar
          </Button>
        </NextLink>
      </Box>
    </CardContent>
  </Card>
);

import { Avatar, Box, Card, CardContent, Grid, Typography } from '@mui/material';
import PeopleAlt from '@mui/icons-material/PeopleAlt';

export const PeopleVaccinated = (props) => (
  <Card
    sx={{ height: '100%' }}
    {...props}
  >
    <CardContent>
      <Grid
        container
        spacing={3}
        sx={{ justifyContent: 'space-between' }}
      >
        <Grid item>
          <Typography
            color="textSecondary"
            gutterBottom
            variant="overline"
          >
            PESSOAS VACINADAS
          </Typography>
          <Typography
            color="textPrimary"
            variant="h4"
          >
            10
          </Typography>
        </Grid>
        <Grid item>
          <Avatar
            sx={{
              backgroundColor: 'warning.main',
              height: 56,
              width: 56
            }}
          >
            <PeopleAlt />
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
          color="warning.main"
          sx={{
            mr: 1
          }}
          variant="body2"
        >
          Faltam/Total (TODO: escolher o q preferirem)
        </Typography>
        <Typography
          color="textSecondary"
          variant="caption"
        >
          20 
        </Typography>
      </Box>
    </CardContent>
  </Card>
);

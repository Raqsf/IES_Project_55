import { Avatar, Box, Card, CardContent, Grid, Typography } from '@mui/material';
import VaccinesIcon from '@mui/icons-material/Vaccines';

export const VaccinesAdministered = (props) => (
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
            VACINAS ADMINISTRADAS
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
              backgroundColor: 'success.main',
              height: 56,
              width: 56
            }}
          >
            <VaccinesIcon />
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
          color="success.main"
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
          50 (Total para o dia de hoje)
        </Typography>
      </Box>
    </CardContent>
  </Card>
);

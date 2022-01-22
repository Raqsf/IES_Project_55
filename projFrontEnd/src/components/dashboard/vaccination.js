//https://www.dgs.pt/saude-publica1/vacinacao.aspx

import { Avatar, Box, Card, CardContent, Grid, Typography, Button } from '@mui/material';
import HealthAndSafetyIcon from '@mui/icons-material/HealthAndSafety';
import NextLink from 'next/link';

export const Vaccination = (props) => (
  <Card {...props}>
    <CardContent>
      <Grid
        container
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
            Vacinação
          </Typography>
        </Grid>
        <Grid item
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
            variant="subtitle1"
        >
            Porque a vacinação é importante?
        </Typography>
      </Box>
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
            É importante porque ...
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
            href="https://www.dgs.pt/saude-publica1/vacinacao.aspx"
          >
            Mais informação
        </Button>
      </Box>
    </CardContent>
  </Card>
);

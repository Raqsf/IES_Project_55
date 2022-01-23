import { Avatar, Box, Card, CardContent, Grid, Typography, Button } from '@mui/material';
import Coronavirus from '@mui/icons-material/Coronavirus';

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
            Importância da Vacinação
          </Typography>
        </Grid>
        <Grid item
            >
          <Avatar
            sx={{
              backgroundColor: 'warning.main',
              height: 56,
              width: 56
            }}
          >
            <Coronavirus />
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
            variant="body1"
        >
            De acordo com a Organização Mundial da Saúde, no mundo, a vacinação <b>evita quatro mortes por minuto</b>. 
            Com a invenção da vacina foi possível <b>erradicar</b> a varíola mundialmente em 1980.
        </Typography>
      </Box>
      <Box sx={{
          pt: 2,
          display: 'flex',
          alignItems: 'center'
        }}
      >
        <Button
            color="warning"
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

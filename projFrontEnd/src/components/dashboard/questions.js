import { Avatar, Box, Card, CardContent, Grid, Typography, Button } from '@mui/material';
import Coronavirus from '@mui/icons-material/Coronavirus';
import { LinkPreview } from '@dhaiwat10/react-link-preview';

export const Questions = (props) => (
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
            Perguntas Frequentes
          </Typography>
        </Grid>
        {/* <Grid item
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
        </Grid> */}
      </Grid> 
      <Box
        sx={{
          pt: 2,
          display: 'flex',
          alignItems: 'center'
        }}
      >
        <LinkPreview url='https://www.sns24.gov.pt/tema/doencas-infecciosas/covid-19/'/>
        {/* <Typography
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
            De acordo com a Organização Mundial da Saúde, no mundo, a vacinação evita quatro mortes por minuto. Com a invenção da vacina foi possível erradicar a varíola mundialmente em 1980.
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
            href="https://www.sns24.gov.pt/tema/doencas-infecciosas/covid-19/"
          >
            Mais informação
        </Button> */}
      </Box>
    </CardContent>
  </Card>
);
import Head from 'next/head';
import { Box, Container, FormControl, FormHelperText, TextField, Button } from '@mui/material';
import { CheckToolbar } from '../components/utente/check-toolbar';
import { DashboardLayout } from '../components/dashboard-layout';

const CheckSchedule = () => (
  <>
    <Head>
      <title>
      Consultar Agendamento | Vacinação
      </title>
    </Head>
    <Box
      component="main"
      sx={{
        flexGrow: 1,
        py: 8
      }}
    >
      <Container maxWidth={false}>
        <CheckToolbar />
        <Box
          sx={{
            display: 'flex',
            justifyContent: 'center',
            pt: 3
          }}
        >
          <Box>
            <form /* onSubmit={handleSubmit} */>
              <div class="row" style={{ width: "350px", marginTop: "20px", marginLeft: "30px", height: "450px" }}>
                  <FormControl variant="outlined">
                      <TextField
                          style={{ marginTop: "10px" }}
                          id="user-number"
                          label="Número de Utente"
                          //onChange={handleChangeModuleType}
                          variant="outlined"
                      />
                      <FormHelperText id="user-number-helper-text">Campo Nº Utente Saúde no Cartão de Cidadão.</FormHelperText> 
                      <TextField
                          style={{ marginTop: "20px" }}
                          id="user-name"
                          label="Nome completo"
                          //onChange={handleChangeDuration}
                          variant="outlined"
                      />
                      <Button 
                        variant="contained"
                        style={{ marginTop: "20px" }}
                        type="submit"
                        href="/vaccination_info"
                      >
                        CONSULTAR
                      </Button>
                  </FormControl>
              </div>
            </form>
          </Box>
        </Box>
      </Container>
    </Box>
  </>
);

CheckSchedule.getLayout = (page) => (
  <DashboardLayout>
    {page}
  </DashboardLayout>
);

export default CheckSchedule;
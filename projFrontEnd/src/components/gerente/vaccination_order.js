import { Box, Card, CardContent, Grid, Typography, Button, FormGroup, FormControlLabel, Checkbox, TextField } from '@mui/material';
//import NextLink from 'next/link';


export const VaccinationOrder = (props) => { 
    const handleChange = (event) => {
        // setChecked(event.target.checked);
        //alert("Funciona");
      };
    return(
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
            Ordem Vacinação
          </Typography>
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
            Doenças
        </Typography>
      </Box>
      <Box
        sx={{
            pt: 2,
            display: 'flex',
            alignItems: 'center'
          }}
      >
        <FormGroup>
            <FormControlLabel control={<Checkbox defaultChecked onChange={handleChange} />} label="Label" />
            <FormControlLabel control={<Checkbox onChange={handleChange} />} label="Label" />
            {/* checked={checked}
            onChange={handleChange}
            inputProps={{ 'aria-label': 'controlled' }} */}
        </FormGroup>
      </Box>
      <Box
        component="form"
        sx={{
            '& .MuiTextField-root': { m: 1, width: '25ch' },
        }}
        noValidate
        autoComplete="off"
        >
        <div>
            <TextField
            id="outlined-number"
            label="Idade"
            type="number"
            defaultValue="65"
            InputLabelProps={{
                shrink: true,
            }}
            />
        </div>
      </Box>
      <Box sx={{
          pt: 2,
          display: 'flex',
          alignItems: 'center'
        }}
      >
        {/* <NextLink href="/agendamento" passHref> */}
          <Button
            color="secondary"
            variant="contained"
            sx={{ mr: 1 }}
          >
            Guardar
          </Button>
        {/* </NextLink> */}
      </Box>
    </CardContent>
  </Card>
);
    }

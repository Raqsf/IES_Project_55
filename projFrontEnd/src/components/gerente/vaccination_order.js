import { Box, Card, CardContent, Typography, Button, FormGroup, FormControlLabel, Checkbox, TextField, LinearProgress } from '@mui/material';
import { useState, useEffect } from 'react';
import api from "../../api";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

toast.configure()
export const VaccinationOrder = (props) => { 
    const {doencas} = props;
    const [age, setAge] = useState(65);
    const [ageCheck, setAgeCheck] = useState(false);
    const [checkboxes, setCheckboxes] = useState(doencas);

    useEffect(() => {
      if(localStorage.getItem("idade") !== null) {
        setAge(JSON.parse(localStorage.getItem("idade")));
        setAgeCheck(true);
      }
    }, [])
    
    useEffect(() => {
      setCheckboxes(doencas);
    },[doencas])

    // console.log(doencas)

    const handleChange = (event) => {
        if (event.target.type == "checkbox") {
          let doe = "";
          // console.log(doencas)
          // console.log(checkboxes)
          for (const d in doencas) {
            if(parseInt(d,10) == event.target.value) {
              doe = doencas[d].doenca;
              break;
            }
          }
          let newDoencas = [...checkboxes];
          newDoencas[event.target.value] = {id: event.target.value, doenca: doe, checked: event.target.checked}
          // console.log(newDoencas)
          setCheckboxes(newDoencas);
        }

        if (event.target.type == "number") {
          setAge(event.target.value);
        }
    };

    const handleChangeAge = (event) => {
      setAgeCheck(!ageCheck);
    }

    const handleSubmit = (event) => {
      let res = []
      let ls = [];
      for (const c of checkboxes) {
        ls.push(c);
        if(c.checked) {
          res.push(c.id)
          // TODO: alterar para mais doenças
        }
      }

      localStorage.setItem("ordem", JSON.stringify(ls));

      let order = {};
      if(res.length > 0) {
        order["doenca"] = res[0];
      }
      if(ageCheck) {
        order["idade"] = age;
        localStorage.setItem("idade", JSON.stringify(age));
      } else {
        localStorage.removeItem("idade");
      }
      console.log(order)

      toast.info("Nova ordem definida", {position: toast.POSITION.TOP_CENTER})
      
    };
    return(
  <Card {...props}>
    <CardContent>
      <Typography
        color="textPrimary"
        variant="h5"
      >
        Ordem Vacinação
      </Typography>
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
        component="form" 
        onSubmit={handleChange}
        validateForm
        autoComplete="off"
      >
      <Box
        sx={{
            pt: 2,
            display: 'flex',
            alignItems: 'center'
          }}
      >
        <FormGroup 
          sx={{ width: '100%', mb: 2 }}
        >
            {checkboxes.length > 0 ? checkboxes.map((doenca) => (
              <FormControlLabel control={<Checkbox onChange={handleChange} checked={doenca.checked} value={doenca.id} />} key={doenca.id} label={doenca.doenca} />
            )) :  
            <Box sx={{ width: '100%' }}>
              <LinearProgress />
            </Box>}
            {/* <FormControlLabel control={<Checkbox defaultChecked onChange={handleChange} />} label="Label" />
            <FormControlLabel control={<Checkbox onChange={handleChange} />} label="Label" /> */}
            {/* checked={checked}
            onChange={handleChange}
            inputProps={{ 'aria-label': 'controlled' }} */}
        </FormGroup>
      </Box>
      <Box>
        <Typography
            color="textSecondary"
            gutterBottom
            variant="body2"
        >
            Idade
        </Typography>
        <FormControlLabel value="age_checked" control={<Checkbox onChange={handleChangeAge} checked={ageCheck} />} label="Ordenar pela idade" />
        <div>
            <TextField
            id="outlined-number"
            label="Idade"
            type="number"
            defaultValue="65"
            InputLabelProps={{
                shrink: true,
            }}
            onChange={handleChange}
            />
        </div>
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
            onClick={handleSubmit}
          >
            Guardar
          </Button>
      </Box>
      </Box>
    </CardContent>
  </Card>
);
    }

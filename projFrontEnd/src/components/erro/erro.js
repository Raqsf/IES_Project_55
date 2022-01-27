import { Stack, Snackbar, Alert } from '@mui/material';

export const ErrorAlert = ({message}) => {

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
          return;
        }
    }

    return (
        <Stack>
            <Snackbar open={true} autoHideDuration={1000} onClose={handleClose}>
            <Alert onClose={handleClose} severity="error" sx={{ width: '100%' }}>
                <h4>{message.status}</h4>
                <p>{message.data}</p>
            </Alert>
            </Snackbar>
        </Stack>
        )
}

export default ErrorAlert;
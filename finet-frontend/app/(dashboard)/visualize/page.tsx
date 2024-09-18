import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid2';
import TextField from '@mui/material/TextField';

export default function Home() {
  return (
    <Paper>
        <Grid container spacing={2}>
            <Grid size={12} sx={{padding: 1}}>
                <TextField fullWidth id="fullWidth" label="Enter a stock" variant="outlined" />
            </Grid>
            <Grid size={12}>
                <Typography variant="body1">
                    This is the dashboard for the Toolpad application. Use the navigation on the left to access the different features of the application.
                </Typography>
            </Grid>
        </Grid>
    </Paper>
  );
}
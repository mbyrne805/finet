import * as React from 'react';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid2';
import TextField from '@mui/material/TextField';
import BasicLineChart from './Chart';
import AutoInput from './AutoInput';

export default function HomePage() {
  

  return (    
    <Paper>
        <Grid container spacing={2}>
            <Grid size={12} sx={{padding: 1}}>
                <TextField fullWidth id="fullWidth" label="Enter stock" variant="outlined" />
            </Grid>
            <Grid size={12}>
              <BasicLineChart />
            </Grid>
            <Grid size={12} sx={{padding: 1}}>
              <TextField fullWidth id="fullWidth" label="Enter tags" variant="outlined" />
            </Grid>
        </Grid>
    </Paper>
  );
}

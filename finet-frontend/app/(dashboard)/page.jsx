'use client';
import * as React from 'react';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid2';
import TextField from '@mui/material/TextField';
import Chart from './Chart';
import Button from '@mui/material/Button';
import Chip from '@mui/material/Chip';
import Stack from '@mui/material/Stack';
import Alert from '@mui/material/Alert';
import CheckIcon from '@mui/icons-material/Check';

// @ts-nocheck
// @ts-ignore
export default function HomePage() {

  const [stock, setStock] = React.useState(null);
  const [stockData, setStockData] = React.useState(null);
  const [tags, setTags] = React.useState(null);
  const [stocks, setStocks] = React.useState(null);
  const [success, setSuccess] = React.useState(false);
  const [failure, setFailure] = React.useState(false);
  const [failureMessage, setFailureMessage] = React.useState("");
  const [fetchFailure, setFetchFailure] = React.useState(false);

  const avKey = process.env.NEXT_PUBLIC_AV_KEY;

  const fetchStockData = () => {
    if (stock) {
      fetch(`https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=${stock}&apikey=${avKey}`)
        .then(response => response.json())
        .then((data) => {
          console.log(data);
          if (data['Monthly Time Series']) {
            setStockData(data)
          } else {
            setFetchFailure(true);
          }
        })
        .catch(error => console.log('Error fetching stock data:'));
    }
  };

  const addStockToNetwork = () => {
    fetch("https://peaceful-escarpment-47280-c10b58068392.herokuapp.com/api/stock", {
      method: 'POST',
      headers: {
      'Content-Type': 'application/json'
      },
      body: JSON.stringify({
      symbol: stock,
      tags: tags,
      relatedStocks: stocks
      })
    })
    .then(response => {
      if (!response.ok) {
        return response.json().then(data => {
          throw new Error(data[0]);
        });
      }
      return response.json();
    })
    .then(data => {
      setSuccess(true);
      setTags(null);
      setStocks(null);
      setStock(null);
      setStockData(null);
    })
    .catch(error => {
      console.log('Error adding stock:', error);
      setFailure(true);
      setFailureMessage(error.message);
    });
  }

  return (    
    <Paper>
      <Grid container spacing={2}>
        <Grid size={12} sx={{padding: 1}}>
          <TextField 
            fullWidth 
            id="fullWidth" 
            label="Query stock" 
            variant="outlined" 
            value={stock || ''} 
            onChange={(e) => setStock(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === 'Enter') {
                fetchStockData();
              }
            }}
          />
          {fetchFailure && <Alert severity="error" onClose={() => setFetchFailure(false)}>Failed to fetch stock data - please use valid symbol.</Alert>}
        </Grid>
        {stockData && stockData != [] && <>
          <Grid size={12} sx={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
            {stockData && stockData != [] && <Chart data={stockData}/>}
          </Grid>
          <Grid size={6} sx={{padding: 1}}>
            <TextField
              fullWidth
              id="fullWidth"
              label="Tags"
              variant="outlined"
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  setTags(prevTags => [...(prevTags || []), e.target.value]);
                }
              }}
              onKeyUp={(e) => {
                if (e.key === 'Enter') {
                  e.target.value = '';
                }
              }}
            />
          </Grid>
          <Grid size={6} sx={{padding: 1}}>
            <TextField 
              fullWidth
              id="fullWidth"
              label="Related stocks" 
              variant="outlined"
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  setStocks(prevStocks => [...(prevStocks || []), e.target.value]);
                }
              }}
              onKeyUp={(e) => {
                if (e.key === 'Enter') {
                  e.target.value = '';
                }
              }}
            />
          </Grid>
          {
            tags &&
            <Grid size={6} sx={{padding: 1}}>
              <Stack direction="row" spacing={1} sx={{ flexWrap: 'wrap' }}>
                {tags.map((tag, index) => (
                  <Chip key={index} label={tag} sx={{ margin: 0.5 }} />
                ))}
              </Stack>
            </Grid>
          }
          {
            stocks &&
            <Grid size={6} sx={{padding: 1}}>
              <Stack direction="row" spacing={1} sx={{ flexWrap: 'wrap' }}>
                {stocks.map((stock, index) => (
                  <Chip key={index} label={stock} sx={{ margin: 0.5 }} />
                ))}
              </Stack>
            </Grid>
          }
          <Grid size={12} sx={{padding: 1}}>
            <Button 
              fullWidth 
              variant="contained" 
              sx={{ height: '56px', fontSize: '18px' }} 
              onClick={addStockToNetwork}
            >
              Add to network
            </Button>
          </Grid>
          </>
        }
        { 
          success &&
          <Grid size={12} sx={{padding: 1}}>
            <Alert severity="success" onClose={() => setSuccess(false)}>
              Save was successful.
            </Alert>
          </Grid>
        }
        {
          failure &&
          <Grid size={12} sx={{padding: 1}}>
            <Alert severity="error" onClose={() => setFailure(false)}>
              {failureMessage}
            </Alert>
          </Grid>
        }
      </Grid>
    </Paper>
  );
}
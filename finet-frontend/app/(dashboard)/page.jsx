'use client';
import * as React from 'react';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid2';
import TextField from '@mui/material/TextField';
import Chart from './Chart';
import Button from '@mui/material/Button';
// @ts-nocheck
// @ts-ignore
export default function HomePage() {

  const [stock, setStock] = React.useState(null);
  const [stockData, setStockData] = React.useState({
    "Meta Data": {
        "1. Information": "Monthly Adjusted Prices and Volumes",
        "2. Symbol": "IBM",
        "3. Last Refreshed": "2024-09-18",
        "4. Time Zone": "US/Eastern"
    },
    "Monthly Adjusted Time Series": {
      "2024-09-18": {
          "1. open": "201.9100",
          "2. high": "218.8400",
          "3. low": "199.3350",
          "4. close": "214.9400",
          "5. adjusted close": "214.9400",
          "6. volume": "48332843",
          "7. dividend amount": "0.0000"
      },
      "2024-08-30": {
          "1. open": "192.8100",
          "2. high": "202.1700",
          "3. low": "181.8100",
          "4. close": "202.1300",
          "5. adjusted close": "202.1300",
          "6. volume": "65453729",
          "7. dividend amount": "1.6700"
      },
      "2024-07-31": {
          "1. open": "173.4500",
          "2. high": "196.2600",
          "3. low": "173.3800",
          "4. close": "192.1400",
          "5. adjusted close": "190.4785",
          "6. volume": "81258646",
          "7. dividend amount": "0.0000"
      },
      "2024-06-28": {
          "1. open": "166.5400",
          "2. high": "178.4599",
          "3. low": "163.5300",
          "4. close": "172.9500",
          "5. adjusted close": "171.4544",
          "6. volume": "70706301",
          "7. dividend amount": "0.0000"
      },
      "2024-05-31": {
          "1. open": "165.6900",
          "2. high": "175.4600",
          "3. low": "162.6200",
          "4. close": "166.8500",
          "5. adjusted close": "165.4072",
          "6. volume": "78620355",
          "7. dividend amount": "1.6700"
      },
      "2024-04-30": {
          "1. open": "190.0000",
          "2. high": "193.2800",
          "3. low": "165.2605",
          "4. close": "166.2000",
          "5. adjusted close": "163.1244",
          "6. volume": "98297181",
          "7. dividend amount": "0.0000"
      },
      "2024-03-28": {
          "1. open": "185.4900",
          "2. high": "199.1800",
          "3. low": "185.1800",
          "4. close": "190.9600",
          "5. adjusted close": "187.4262",
          "6. volume": "99921776",
          "7. dividend amount": "0.0000"
      },
      "2024-02-29": {
          "1. open": "183.6300",
          "2. high": "188.9500",
          "3. low": "178.7500",
          "4. close": "185.0300",
          "5. adjusted close": "181.6059",
          "6. volume": "88679550",
          "7. dividend amount": "1.6600"
      },
      "2024-01-31": {
          "1. open": "162.8300",
          "2. high": "196.9000",
          "3. low": "157.8850",
          "4. close": "183.6600",
          "5. adjusted close": "178.6527",
          "6. volume": "128121557",
          "7. dividend amount": "0.0000"
      },
      "2023-12-29": {
          "1. open": "158.4100",
          "2. high": "166.3400",
          "3. low": "158.0000",
          "4. close": "163.5500",
          "5. adjusted close": "159.0909",
          "6. volume": "87358302",
          "7. dividend amount": "0.0000"
      },
      "2023-11-30": {
          "1. open": "145.0000",
          "2. high": "158.6000",
          "3. low": "144.4500",
          "4. close": "158.5600",
          "5. adjusted close": "154.2370",
          "6. volume": "78460252",
          "7. dividend amount": "1.6600"
      },
      "2023-10-31": {
          "1. open": "140.0400",
          "2. high": "144.7600",
          "3. low": "135.8700",
          "4. close": "144.6400",
          "5. adjusted close": "139.1214",
          "6. volume": "94386980",
          "7. dividend amount": "0.0000"
      },
    }
  });
  const [tags, setTags] = React.useState(null);

  const fetchStockData = () => {
    if (stock) {
      fetch(`https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=${stock}&apikey=demo`)
        .then(response => response.json())
        .then(data => setStockData(data))
        .catch(error => console.log('Error fetching stock data:'));
    }
  };

  const addStockToNetwork = () => {
    fetch("http://localhost:8080/api/stock", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        stock: stock,
        tags: tags
      })
    })
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
              value={tags || ''} 
            />
          </Grid>
          <Grid size={6} sx={{padding: 1}}>
            <TextField fullWidth id="fullWidth" label="Related stocks" variant="outlined" />
          </Grid>
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
      </Grid>
    </Paper>
  );
}
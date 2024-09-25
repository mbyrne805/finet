import * as React from 'react';
import { LineChart } from '@mui/x-charts/LineChart';
import { ContinuousColorLegend } from '@mui/x-charts';

// @ts-nocheck
// @ts-ignore
export default function Chart({ data }) {
  console.log(data)
  const opens = Object.keys(data['Monthly Time Series'])
    .slice(0, 12)
    .map(key => parseFloat(data['Monthly Time Series'][key]['1. open']))
    .reverse();

  return (
    <LineChart
      xAxis={[{ data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12] }]}
      series={[
        {
          data: opens,
        },
      ]}
      width={500}
      height={300}
    />
  );
}
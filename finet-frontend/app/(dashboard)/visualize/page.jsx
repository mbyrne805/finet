'use client';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid2';
import TextField from '@mui/material/TextField';
import React, { useEffect } from 'react';
import { ReactFlow } from '@xyflow/react';

import '@xyflow/react/dist/style.css';

const initialNodes = [
  { id: '1', position: { x: 0, y: 0 }, data: { label: '1' } },
  { id: '2', position: { x: 0, y: 100 }, data: { label: '2' } },
];
const initialEdges = [{ id: 'e1-2', source: '1', target: '2' }];

export default function Home() {

  const [nodes, setNodes] = React.useState(initialNodes);
  const [edges, setEdges] = React.useState(initialEdges);

  useEffect(() => {
    fetch('http://localhost:8080/api/stock')
      .then(response => response.json())
      .then(data => {
        console.log(data);
        const newNodes = [];
        const newEdges = [];
        const tagSet = new Set();

        data.forEach((stock) => {
          newNodes.push({
            id: `stock-${stock.symbol}`,
            position: { x: Math.random() * 400, y: Math.random() * 400 },
            data: { label: stock.symbol },
          });

          stock.tags.forEach((tag) => {
            if (!tagSet.has(tag)) {
              tagSet.add(tag);
              newNodes.push({
                id: `tag-${tag.name}`,
                position: { x: Math.random() * 400, y: Math.random() * 400 },
                data: { label: tag.name },
              });
            }
            newEdges.push({ id: `edge-${stock.symbol}-${tag.name}`, source: `stock-${stock.symbol}`, target: `tag-${tag.name}` });
          });

          stock.stocks.forEach((relatedStock => {
            newEdges.push({ id: `edge-${stock.symbol}-${relatedStock.name}`, source: `stock-${stock.symbol}`, target: `stock-${relatedStock.symbol}` });
          }));
        });
        console.log(newNodes)
        setNodes(newNodes);
      })
      .catch((error) => {
        console.log('Error:', error);
      });
  }, []);


  return (
    <Paper>
      <div style={{ width: '100vw', height: '100vh', color: "teal" }}>
        <ReactFlow nodes={nodes} edges={edges} />
      </div>
    </Paper>
  );
}
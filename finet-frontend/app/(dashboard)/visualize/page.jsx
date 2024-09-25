'use client';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid2';
import TextField from '@mui/material/TextField';
import React, { useEffect } from 'react';
import { ReactFlow, Background, Controls, MiniMap, useNodesState, useEdgesState } from '@xyflow/react';

import '@xyflow/react/dist/style.css';

const initialNodes = [
  { id: '1', position: { x: 0, y: 0 }, data: { label: '1' } },
  { id: '2', position: { x: 0, y: 100 }, data: { label: '2' } },
];
const initialEdges = [{ id: 'e1-2', source: '1', target: '2' }];

export default function Home() {

  const [nodes, setNodes, onNodesChange] = useNodesState([]);
  const [edges, setEdges, onEdgesChange] = useEdgesState([]);
  const isMounted = React.useRef(false);

  const handleNodeDragStop = (event, node) => {
    const [type, symbol] = node.id.split('-');
    const nodeData = nodes.find(n => n.id === node.id)?.data || {};
    console.log(nodeData.stockId);
    if (type === 'stock') {
      fetch(`http://localhost:8080/api/stock/${symbol}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          id: nodeData.stockId != null ? nodeData.stockId : null,
          symbol: symbol ? symbol : '',
          tags: Array.isArray(nodeData.tags) ? nodeData.tags.map(tag => tag.name) : [],
          relatedStocks: Array.isArray(nodeData.stocks) ? nodeData.stocks.map(stock => stock.symbol) : [],
          xPos: node.position.x ? node.position.x : 0,
          yPos: node.position.y ? node.position.y : 0,
          test: "test"
        }),
      })
        .then(response => response.json())
        .then(data => {
          console.log('Success:', data);
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    } else if (type === 'tag') {
      const name = symbol;
      console.log(nodeData);
      fetch(`http://localhost:8080/api/tag/${name}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          id: nodeData.tagId != null ? nodeData.tagId : null,
          name: name ? name : '',
          xPos: node.position.x ? node.position.x : 0,
          yPos: node.position.y ? node.position.y : 0,
        }),
      })
        .then(response => response.json())
        .then(data => {
          console.log('Success:', data);
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    }

    console.log(`Node ${symbol} was dragged to position:`, node.position);

  };

  const handleNodesChange = (changes) => {
    changes.forEach(change => {
      if (change.type === 'position' && change.dragging === false) {
        handleNodeDragStop(null, change);
      }
    });
    onNodesChange(changes);
  };

  useEffect(() => {
    if (isMounted.current == true) {
      return;
    }
    isMounted.current = true;
    fetch('http://localhost:8080/api/stock')
      .then(response => response.json())
      .then(data => {
        const newNodes = [];
        const newEdges = [];
        const tagSet = new Set();

        data.forEach((stock) => {
          console.log(stock)
          const nodeSize = 100;

          const isOverlapping = (newNode, existingNodes) => {
            return existingNodes.some(node => {
              const dx = newNode.position.x - node.position.x;
              const dy = newNode.position.y - node.position.y;
              return Math.abs(dx) < nodeSize && Math.abs(dy) < nodeSize;
            });
          };

          const getNonOverlappingPosition = (existingNodes) => {
            let position;
            do {
              position = { x: Math.random() * 1000, y: Math.random() * 1000 };
            } while (isOverlapping({ position }, existingNodes));
            return position;
          };

          newNodes.push({
            id: `stock-${stock.symbol}`,
            position: stock.xpos && stock.ypos ? { x: stock.xpos, y: stock.ypos } : getNonOverlappingPosition(newNodes),
            data: { stockId: stock.id, label: stock.symbol, tags: stock.tags, stocks: stock.stocks },
            draggable: true,
          });

          stock.tags.forEach((tag) => {
            if (!tagSet.has(tag.name)) {
              tagSet.add(tag.name);

              newNodes.push({
                id: `tag-${tag.name}`,
                position: tag.xpos && tag.ypos ? { x: tag.xpos, y: tag.ypos } : getNonOverlappingPosition(newNodes),
                data: { tagId: tag.id, label: tag.name },
                draggable: true,
              });
            }
            newEdges.push({ id: `edge-${stock.symbol}-${tag.name}`, source: `stock-${stock.symbol}`, target: `tag-${tag.name}` });
          });

          stock.stocks.forEach((relatedStock => {
            newEdges.push({ id: `edge-${stock.symbol}-${relatedStock.symbol}`, source: `stock-${stock.symbol}`, target: `stock-${relatedStock.symbol}` });
          }));
        });

        setNodes(newNodes);
        setEdges(newEdges);
      })
      .catch((error) => {
        console.log('Error:', error);
      });
  }, []);


  return (
    <Paper>
      <div style={{ width: '100vw', height: '100vh', color: "teal" }}>
        <ReactFlow nodes={nodes} edges={edges} onNodesChange={handleNodesChange} onEdgesChange={onEdgesChange} fitView nodesDraggable={true} nodesConnectable={true} elementsSelectable={true}>
          <MiniMap />
          <Controls />
          <Background />
        </ReactFlow>
      </div>
    </Paper>
  );
}
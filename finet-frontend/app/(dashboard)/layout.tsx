import * as React from 'react';
import { DashboardLayout } from '@toolpad/core/DashboardLayout';
import { PageContainer } from '@toolpad/core/PageContainer';
import Paper from '@mui/material/Paper';

export default function Layout(props: { children: React.ReactNode }) {
  return (
    <DashboardLayout>
        <PageContainer sx={{paddingLeft: "30em"}}>{props.children}</PageContainer>
    </DashboardLayout>
  );
}  

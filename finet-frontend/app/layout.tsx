import * as React from 'react';
import { Suspense } from 'react';
import { AppProvider } from '@toolpad/core/nextjs';
import { AppRouterCacheProvider } from '@mui/material-nextjs/v14-appRouter';
import QueryStatsIcon from '@mui/icons-material/QueryStats';
import StreamIcon from '@mui/icons-material/Stream';
import type { Navigation } from '@toolpad/core';

import theme from '../theme';

const NAVIGATION: Navigation = [
  {
    kind: 'header',
    title: 'Options',
  },
  {
    segment: '',
    title: 'Query',
    icon: <QueryStatsIcon />,
  },

  {
    segment: 'visualize',
    title: 'Visualize',
    icon: <StreamIcon />,
  }
];

const BRANDING = {
  title: 'FINET',
};



export default function RootLayout(props: { children: React.ReactNode }) {
  return (
    <html lang="en" data-toolpad-color-scheme="light" suppressHydrationWarning>
      <body>
        <Suspense>
          <AppRouterCacheProvider options={{ enableCssLayer: true }}>
            <AppProvider
              navigation={NAVIGATION}
              branding={BRANDING}
              theme={theme}
            >
              {props.children}
            </AppProvider>
          </AppRouterCacheProvider>
        </Suspense>
      </body>
    </html>
  );
}

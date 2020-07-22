import React from 'react';
import { AppShell } from '../core/components/AppShell';

export default {
    title: 'App Shell',
    component: AppShell,
  };

export const withDiv = () => (
    <AppShell>
        <div> first child</div>
        <div> second child</div>
    </AppShell>
);
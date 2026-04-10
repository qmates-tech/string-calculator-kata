import React from 'react'
import { createRoot } from 'react-dom/client'
import { StringCalculatorComponent } from './StringCalculatorComponent'

createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <StringCalculatorComponent />
  </React.StrictMode>
)

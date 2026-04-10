import '@testing-library/jest-dom'
import { act, render, screen } from '@testing-library/react'
import { describe, expect, it } from 'vitest'
import { ScreenCalculatorComponent } from './ScreenCalculatorComponent'

describe('ScreenCalculatorComponent', () => {
  it('renders the input, calculate button, and result label', () => {
    render(<ScreenCalculatorComponent />)

    expect(screen.getByRole('textbox')).toBeInTheDocument()
    expect(screen.getByRole('button', { name: 'Calculate' })).toBeInTheDocument()
    expect(screen.getByText('Result:')).toBeInTheDocument()
  })

  it('sample: typing a value and clicking Calculate', async () => {
    render(<ScreenCalculatorComponent />)

    const input = screen.getByRole('textbox')
    const button = screen.getByRole('button', { name: 'Calculate' })

    await act(async () => {
      input.focus()
      // userEvent or fireEvent calls will go here
    })

    await act(async () => {
      button.click()
    })

    // assert on screen.getByText(...) or screen.getByRole(...) here
  })
})

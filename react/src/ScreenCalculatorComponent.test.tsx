import '@testing-library/jest-dom'
import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, expect, it } from 'vitest'
import { ScreenCalculatorComponent } from './ScreenCalculatorComponent'

describe('ScreenCalculatorComponent', () => {

  it('single digit renders itself', async () => {
    render(<ScreenCalculatorComponent />)

    await userEvent.type(screen.getByRole('textbox'), '1')
    await userEvent.click(screen.getByRole('button', { name: 'Calculate' }))

    expect(screen.getByText('1')).toBeInTheDocument()
  })

})

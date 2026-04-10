import '@testing-library/jest-dom'
import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, expect, it } from 'vitest'
import { StringCalculatorComponent } from './StringCalculatorComponent'

describe('StringCalculatorComponent', () => {

  describe('step 1: the simplest thing', () => {

    it('with no input renders 0', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('0')).toBeInTheDocument()
    })

    it('single digit 0 renders 0', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "0");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('0')).toBeInTheDocument()
    })

    it('single digit 1 renders 1', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('1')).toBeInTheDocument()
    })

    it('single digit 5 renders 5', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "5");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('5')).toBeInTheDocument()
    })

    it('single number 10 renders 10', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "10");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('10')).toBeInTheDocument()
    })

    it('single number 100 renders 100', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "100");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('100')).toBeInTheDocument()
    })

    it('two numbers 1,2 renders 3', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1,2");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('3')).toBeInTheDocument()
    })

    it('two numbers 0,0 renders 0', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "0,0");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('0')).toBeInTheDocument()
    })

    it('two numbers 5,5 renders 10', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "5,5");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('10')).toBeInTheDocument()
    })

    it('two numbers 10,20 renders 30', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "10,20");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('30')).toBeInTheDocument()
    })

    it('two numbers 100,200 renders 300', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "100,200");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('300')).toBeInTheDocument()
    })

  })

  describe('step 2: handle an unknown amount of numbers', () => {

    it('three numbers 1,2,3 renders 6', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1,2,3");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('6')).toBeInTheDocument()
    })

    it('four numbers 1,2,3,4 renders 10', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1,2,3,4");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('10')).toBeInTheDocument()
    })

    it('five numbers 1,2,3,4,5 renders 15', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1,2,3,4,5");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('15')).toBeInTheDocument()
    })

    it('six numbers 10,20,30,40,50,60 renders 210', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "10,20,30,40,50,60");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('210')).toBeInTheDocument()
    })

    it('many zeros return 0', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "0,0,0,0,0");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('0')).toBeInTheDocument()
    })

    it('a single number still works as a degenerate case of many numbers', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "42");
      await userEvent.click(screen.getByRole("button", { name: "Calculate" }));
      expect(screen.getByText('42')).toBeInTheDocument()
    })

  })

})

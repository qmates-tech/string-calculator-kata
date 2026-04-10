import '@testing-library/jest-dom'
import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { describe, expect, it } from 'vitest'
import { StringCalculatorComponent } from './StringCalculatorComponent'

describe('StringCalculatorComponent', () => {

  describe('step 1: the simplest thing', () => {

    it('with no input renders 0', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('0'));
    })

    it('single digit 0 renders 0', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "0");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('0'));
    })

    it('single digit 1 renders 1', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('1'));
    })

    it('single digit 5 renders 5', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "5");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('5'));
    })

    it('single number 10 renders 10', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "10");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('10'));
    })

    it('single number 100 renders 100', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "100");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('100'));
    })

    it('two numbers 1,2 renders 3', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1,2");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('3'));
    })

    it('two numbers 0,0 renders 0', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "0,0");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('0'));
    })

    it('two numbers 5,5 renders 10', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "5,5");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('10'));
    })

    it('two numbers 10,20 renders 30', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "10,20");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('30'));
    })

    it('two numbers 100,200 renders 300', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "100,200");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('300'));
    })

  })

  describe('step 2: handle an unknown amount of numbers', () => {

    it('three numbers 1,2,3 renders 6', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1,2,3");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('6'));
    })

    it('four numbers 1,2,3,4 renders 10', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1,2,3,4");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('10'));
    })

    it('five numbers 1,2,3,4,5 renders 15', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1,2,3,4,5");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('15'));
    })

    it('six numbers 10,20,30,40,50,60 renders 210', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "10,20,30,40,50,60");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('210'));
    })

    it('many zeros renders 0', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "0,0,0,0,0");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('0'));
    })

    it('a single number still works as a degenerate case of many numbers', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "42");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('42'));
    })

  })

  describe('step 3: handle new lines between numbers', () => {

    it('two numbers separated by newline 1\\n2 renders 3', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1\\n2");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('3'));
    })

    it('three numbers with mixed delimiters 1\\n2,3 renders 6', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1\\n2,3");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('6'));
    })

    it('three numbers with mixed delimiters 1,2\\n3 renders 6', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1,2\\n3");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('6'));
    })

    it('three numbers all separated by newlines 1\\n2\\n3 renders 6', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1\\n2\\n3");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('6'));
    })

    it('two multi-digit numbers separated by newline 10\\n20 renders 30', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "10\\n20");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('30'));
    })

    it('five numbers all separated by newlines 1\\n2\\n3\\n4\\n5 renders 15', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1\\n2\\n3\\n4\\n5");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('15'));
    })

    it('single number with no delimiter still renders itself', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "7");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('7'));
    })

  })

  describe('step 4: support different delimiters', () => {

    it('//;\\n1;2 renders 3', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "//;\\n1;2");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('3'));
    })

    it('//;\\n1;2;3 renders 6', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "//;\\n1;2;3");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('6'));
    })

    it('//-\\n1-2-3 renders 6', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "//-\\n1-2-3");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('6'));
    })

    it('//|\\n5|5 renders 10', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "//|\\n5|5");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('10'));
    })

    it('//.\\n1.2.3 renders 6 (dot delimiter is properly escaped)', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "//.\\n1.2.3");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('6'));
    })

    it('//;\\n10;20;30 renders 60', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "//;\\n10;20;30");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('60'));
    })

    it('//;\\n0;0;0 renders 0', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "//;\\n0;0;0");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('0'));
    })

    it('//;\\n5 renders 5 (single number with custom delimiter)', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "//;\\n5");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('5'));
    })

    it('comma delimiter still works without header (regression)', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1,2,3");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('6'));
    })

    it('newline delimiter still works without header (regression)', async () => {
      render(<StringCalculatorComponent />);
      await userEvent.type(screen.getByRole("textbox"), "1\\n2\\n3");
      await userEvent.click(screen.getByRole("button", { name: "Add" }));
      await waitFor(() => expect(screen.getByTestId('result')).toHaveTextContent('6'));
    })

  })

})

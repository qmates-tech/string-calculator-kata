import React, { useState } from 'react'

export function StringCalculatorComponent(): React.JSX.Element {
  const [input, setInput] = useState('')
  const [sum, setSum] = useState<string>('')

  const calculateSum = () => {
    if (input === '') setSum("0")
    else setSum(String(input.split(',').reduce((sum, n) => sum + parseInt(n, 10), 0)))
  }
  
  const onClick = async () => {
    await simulateComplexSlowOperation()
    calculateSum()
  }

  return (
    <div>
      <div>
        <input
          type="text"
          style={{ width: '400px' }}
          value={input}
          onChange={(e) => setInput(e.target.value)}
        />
        &nbsp;
        <button onClick={onClick}>Calculate</button>
      </div>
      <div>
        <label>Result:</label>
        &nbsp;
        <span>{sum}</span>
      </div>
    </div>
  )
}

async function simulateComplexSlowOperation(): Promise<void> {
  return new Promise((resolve) => setTimeout(resolve, 100))
}


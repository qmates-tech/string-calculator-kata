import React, { useState } from 'react'

export function StringCalculatorComponent(): React.JSX.Element {
  const [input, setInput] = useState('')
  const [result, setResult] = useState('')

  return (
    <div>
      <div>
        <input
          type="text"
          style={{ width: '400px' }}
          value={input}
          onChange={(e) => setInput(e.target.value)}
        />
        <button onClick={() => setResult(input)}>Calculate</button>
      </div>
      <div>
        <label>Result:</label>
        <span>{result}</span>
      </div>
    </div>
  )
}

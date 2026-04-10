import React from 'react'

export function ScreenCalculatorComponent(): React.JSX.Element {
  return (
    <div>
      <div>
        <input type="text" style={{ width: '400px' }} />
        <button>Calculate</button>
      </div>
      <div>
        <label>Result:</label>
        <span></span>
      </div>
    </div>
  )
}

import React, { useState } from 'react';
import './App.css'

import Checker from './Checker';
import Request from './Request';


export default function App() {

  const [text, setText] = useState('');
  const [displayText, setDisplayText] = useState('');
  const [checkBoxes, setCheckBoxes] = useState({
    SwitchBox: false,
    PSBox: false,
    XboxBox: false,
    SteamBox: false
  });

  function handleChange(text){
    setText(text.target.value);
  }

  function handleDisplayeChange(){
    setDisplayText(text);
  }

  function handleCheckBoxChange(e){
    const{ id, checked } = e.target;
    setCheckBoxes(prev => ({...prev, [id]: checked}));
  }

  return(
      <>
      <h1>Game Searcher</h1>
      <p>Mark the platforms you'd like to search by clicking on the images below, enter the game name and press "Send"</p>
      <Checker checkBoxID="SwitchBox" checkBoxClass="hidden-checkbox" labelID="SwitchImage" labelClass="custom-checkbox-image" checked={checkBoxes.SwitchBox} onChange={handleCheckBoxChange} />
    <Checker checkBoxID="PSBox" checkBoxClass="hidden-checkbox" labelID="PSImage" labelClass="custom-checkbox-image" checked={checkBoxes.PSBox} onChange={handleCheckBoxChange} />
    <Checker checkBoxID="XboxBox" checkBoxClass="hidden-checkbox" labelID="XboxImage" labelClass="custom-checkbox-image" checked={checkBoxes.XboxBox} onChange={handleCheckBoxChange}/>
    <Checker checkBoxID="SteamBox" checkBoxClass="hidden-checkbox" labelID="SteamImage" labelClass="custom-checkbox-image" checked={checkBoxes.SteamBox} onChange={handleCheckBoxChange} />
    <input type='text' placeholder='Insert Game Name' onChange={handleChange} value={text} ></input>
    <button onClick={handleDisplayeChange}>Send</button>

    {displayText && checkBoxes.PSBox && <Request platform="playstation" game={displayText.replaceAll(" ", "_")}/>}
    {displayText && checkBoxes.XboxBox && <Request platform="xbox" game={displayText.replaceAll(" ", "_")}/>}
    {displayText && checkBoxes.SwitchBox && <Request platform="nintendo" game={displayText.replaceAll(" ", "_")}/>}
    {displayText && checkBoxes.SteamBox && <Request platform="steam" game={displayText.replaceAll(" ", "_")}/>}
    
      
    </>
  
);
}


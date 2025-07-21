import React from "react";


export default function Checker(props){
    const {checkBoxID, checkBoxClass, labelID,  labelClass, checked, onChange} = props;
    return(
    <div>
        <input type="checkbox" id={checkBoxID} className={checkBoxClass}
        onChange={onChange}
        checked={checked}
        ></input>
        <label htmlFor={checkBoxID} id={labelID} className={labelClass}></label>
    </div>
    );
}
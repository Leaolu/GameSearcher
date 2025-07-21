import axios from "axios";
import React, { useState, useEffect } from "react";


export default function Request(props){
   const {platform, game} = props;
   const [responseData, setResponseData] = useState("");
    
    useEffect(() => {
        axios.get("http://localhost:8080/api/games/v1/"+platform+"/"+game)
        .then(response => setResponseData(response.data))
        .catch(error => setResponseData("Erro: " +error.message));
    }, [platform, game]);

    const resp = JSON.stringify({responseData}, null, 1);

    return(
        <p>Your response on {platform}: {resp}</p>
    );
}
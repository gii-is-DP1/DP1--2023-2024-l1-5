import React from 'react';
import "../static/css/player/gameRules.css"

export default function GameRules() {

  const imagePaths = [
    'https://i.imgur.com/P5d3qbK.jpeg',
    'https://i.imgur.com/xg0Ip2C.jpeg',
    'https://i.imgur.com/poq7e8a.jpeg',
    'https://i.imgur.com/doqI44d.jpeg',
    'https://i.imgur.com/SPyjIzq.jpeg',
    'https://i.imgur.com/FM0lzzV.jpeg',
    'https://i.imgur.com/dnbylbJ.jpeg',
    'https://i.imgur.com/ENbj5D3.jpeg',
  ];
  return (
    <div className='wallpaper'>
      <div className="image-view">
        <div className="image-row">
          {imagePaths.slice(0, 4).map((path, index) => (
            <div key={index} className="image-container">
              <img className="image" src={path} alt={`Imagen ${index + 1}`} />
            </div>
          ))}
        </div>
        <div className="image-row">
          {imagePaths.slice(4).map((path, index) => (
            <div key={index + 4} className="image-container">
              <img className="image" src={path} alt={`Imagen ${index + 5}`} />
            </div>
          ))}
        </div>
      </div>
    </div>
    
  );
};
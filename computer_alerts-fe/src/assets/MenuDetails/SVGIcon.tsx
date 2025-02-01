import {useState} from "react"
import MenuDetails from "./MenuDetails"


const SVGIcon: React.FC = () => {
const [formPopup, setFormPopup] = useState(false);

const handleClick = () => {
    setFormPopup(!formPopup); // Toggle menu visibility
  };


  return (
    <div style={{ position: 'relative' }}>
      {/* SVG Button */}
      <svg xmlns="http://www.w3.org/2000/svg" onClick={handleClick} width="40" height="45" color="white"  
      viewBox="0 0 24 24" fill="none" stroke="currentColor"  stroke-linecap="round" stroke-linejoin="round" className="lucide lucide-align-justify"><path d="M3 12h18"/><path d="M3 18h18"/><path d="M3 6h18"/></svg>

      {/* Conditionally render the Menu */}
      {formPopup && <MenuDetails/>}
    </div>
  );
}

export default SVGIcon;


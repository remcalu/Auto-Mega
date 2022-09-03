import { Box, Card, CardContent, Link, Stack, Typography } from '@mui/material';
import Vehicle from '../../../../../types/Vehicle';
import "./VehicleListCard.css"

interface IProps {
  vehicle: Vehicle
}

export default function VehicleListCard(props : IProps) {
    return (
    <Box>
      <Link underline="none" rel="noopener noreferrer" target="_blank" href={props.vehicle.link}>
        <Card className="VehicleListCard">
          <CardContent className="VehicleListCard-content">
            <Stack direction="row" alignItems="center" justifyContent="space-around" spacing={4}>
              <Box width="50px">
                <Typography align='left' color="black">${props.vehicle.price}</Typography>
              </Box>
              <Box width="70px">
                <Typography align='left' color="black">{props.vehicle.brand}</Typography>
              </Box>
              <Box width="80px">
                <Typography align='left' color="black">{props.vehicle.model}</Typography>
              </Box>
              <Box width="70px">
                <Typography align='left' color="black">{props.vehicle.mileage === 0 ? "New" : 
                (props.vehicle.mileage === -1 ? "N/A" : props.vehicle.mileage + "km")}</Typography>
              </Box>
              <Box width="50px">
                <Typography align='left' color="black">{props.vehicle.year === -1 ? "N/A" : props.vehicle.year}</Typography>
              </Box>
              <Box width="50px">
                <Typography align='left' color="black">{props.vehicle.isPrivateDealer ? "Private" : "Dealer"}</Typography>
              </Box>
            </Stack>
          </CardContent>
        </Card>
      </Link>
    </Box>
  );
}
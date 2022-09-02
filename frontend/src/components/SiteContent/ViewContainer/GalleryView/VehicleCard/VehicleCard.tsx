import './VehicleCard.css';
import Vehicle from '../../../../../types/Vehicle';
import { Box, Button, Card, CardContent, CardMedia, List, ListItem, ListItemText, Stack, Typography } from '@mui/material';
import { Build, Monitor, Person, Speed, TimeToLeave } from '@mui/icons-material';
import { formatIsPrivateDealer, formatWithCommas, getColorFromWebsite } from '../../../../../util/VehicleCardUtil';

interface IProps {
  vehicle: Vehicle
}

export default function VehicleCard(props : IProps) {
  return (
    <Box>
      <Card className="VehicleCard">
        <CardMedia className="VehicleCard-image" component="img" image={props.vehicle.imageLink} alt="vehicle image" />
        <CardContent className="VehicleCard-content">
          <Typography align='left' variant="h4" component="div">
            ${formatWithCommas(props.vehicle.price)}
          </Typography>
          <List dense className="VehicleCard-content-info">
            <ListItem disablePadding>
              <Stack direction="row" alignItems="center" gap={1}>
                <TimeToLeave className="VehicleCard-content-info-icon"/>
                <ListItemText sx={{ margin: 0 }} primary={`${props.vehicle.year} ${props.vehicle.brand} ${props.vehicle.model}`}/>
              </Stack>
            </ListItem>
            <ListItem disablePadding>
              <Stack direction="row" alignItems="center" gap={1}>
                <Speed className="VehicleCard-content-info-icon"/>
                <ListItemText className="VehicleCard-content-info-text" primary={props.vehicle.mileage === 0 ? "New" : formatWithCommas(props.vehicle.mileage)}/>
              </Stack>
            </ListItem>
            <ListItem disablePadding>
              <Stack direction="row" alignItems="center" gap={1}>
                <Build className="VehicleCard-content-info-icon"/>
                <ListItemText className="VehicleCard-content-info-text" primary={props.vehicle.transmission}/>
              </Stack>
            </ListItem>
            <ListItem disablePadding>
              <Stack direction="row" alignItems="center" gap={1}>
                <Person className="VehicleCard-content-info-icon"/>
                <ListItemText className="VehicleCard-content-info-text" primary={formatIsPrivateDealer(props.vehicle.isPrivateDealer)}/>
              </Stack>
            </ListItem>
            <ListItem disablePadding>
              <Stack direction="row" alignItems="center" gap={1}>
                <Monitor className="VehicleCard-content-info-icon"/>
                <ListItemText className="VehicleCard-content-info-text" color={getColorFromWebsite(props.vehicle.website)} primary={props.vehicle.website}/>
              </Stack>
            </ListItem>
          </List>
          <Button variant="text" rel="noopener noreferrer" target="_blank" href={props.vehicle.link} size="small">View Ad</Button>
        </CardContent>
      </Card>
    </Box>
  );
}
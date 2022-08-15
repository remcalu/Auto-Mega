import './VehicleItem.css';
import Vehicle from '../../types/Vehicle';
import { Button, Card, CardContent, CardMedia, List, ListItem, ListItemText, Stack, Typography } from '@mui/material';
import { AccessTime, Monitor, Person, Speed, TimeToLeave } from '@mui/icons-material';
import { formatIsPrivateDealer, formatWithCommas, getColorFromWebsite } from '../../util/VehicleItemUtil';

interface IProps {
  vehicle: Vehicle
}

export default function VehicleItem(props : IProps) {
  return (
    <div>
      <Card sx={{ padding: 0, width: 215 }}>
        <CardMedia
          component="img"
          height="100"
          width="100"
          image={props.vehicle.imageLink}
          alt="vehicle"
        />
        <CardContent sx={{ paddingTop: "8px", paddingBottom: "8px !important" }}>
        <Typography align='left' variant="h4" component="div">
          ${formatWithCommas(props.vehicle.price)}
        </Typography>
        <List dense sx={{ padding: 0, width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
          <ListItem disablePadding>
            <Stack direction="row" alignItems="center" gap={1}>
              <TimeToLeave sx={{ fontSize: 15}} />
              <ListItemText sx={{ margin: 0 }} primary={`${props.vehicle.year} ${props.vehicle.brand} ${props.vehicle.model}`}/>
            </Stack>
          </ListItem>
          <ListItem disablePadding>
            <Stack direction="row" alignItems="center" gap={1}>
              <Speed sx={{ fontSize: 15}} />
              <ListItemText sx={{ margin: 0 }} primary={formatWithCommas(props.vehicle.mileage)}/>
            </Stack>
          </ListItem>
          <ListItem disablePadding>
            <Stack direction="row" alignItems="center" gap={1}>
              <Person sx={{ fontSize: 15}} />
              <ListItemText sx={{ margin: 0 }} primary={formatIsPrivateDealer(props.vehicle.isPrivateDealer)}/>
            </Stack>
          </ListItem>
          <ListItem disablePadding>
            <Stack direction="row" alignItems="center" gap={1}>
              <Monitor sx={{ fontSize: 15}} />
              <ListItemText sx={{ margin: 0 }} color={getColorFromWebsite(props.vehicle.website)} primary={props.vehicle.website}/>
            </Stack>
          </ListItem>
          <ListItem disablePadding>
            <Stack direction="row" alignItems="center" gap={1}>
              <AccessTime sx={{ fontSize: 15}} />
              <ListItemText sx={{ margin: 0 }} primary={props.vehicle.dateScraped}/>
            </Stack>
          </ListItem>
        </List>
        <Button href={props.vehicle.link} size="small">View Ad</Button>
        </CardContent>
      </Card>


      {/* {props.vehicle.id}
      {props.vehicle.brand}
      {props.vehicle.model}
      {props.vehicle.website}
      {props.vehicle.dateScraped}
      {props.vehicle.link}
      {props.vehicle.imageLink}
      {props.vehicle.isPrivateDealer}
      {props.vehicle.instantScraped}
      {props.vehicle.mileage}
      {props.vehicle.price}
      {props.vehicle.year} */}
    </div>
  );
}
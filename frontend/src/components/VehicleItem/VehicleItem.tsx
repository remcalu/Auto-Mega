import './VehicleItem.css';
import Vehicle from '../../types/Vehicle';
import { Box, Card, CardContent, CardMedia, List, ListItem, ListItemText, Stack, Typography } from '@mui/material';
import { AccessTime, Monitor, Person, Speed, TimeToLeave } from '@mui/icons-material';
import { formatIsPrivateDealer, formatWithCommas, getColorFromWebsite } from '../../util/VehicleItemUtil';
import { CustomButtonBorderless } from '../StyledMuiComponents/CustomButtons/CustomButton/CustomButton';

interface IProps {
  vehicle: Vehicle
}

export default function VehicleItem(props : IProps) {
  return (
    <Box>
      <Card className="VehicleItem">
        <CardMedia className="VehicleItem-image" component="img" image={props.vehicle.imageLink} alt="vehicle image" />
        <CardContent className="VehicleItem-content">
          <Typography align='left' variant="h4" component="div">
            ${formatWithCommas(props.vehicle.price)}
          </Typography>
          <List dense className="VehicleItem-content-info">
            <ListItem disablePadding>
              <Stack direction="row" alignItems="center" gap={1}>
                <TimeToLeave className="VehicleItem-content-info-icon"/>
                <ListItemText sx={{ margin: 0 }} primary={`${props.vehicle.year} ${props.vehicle.brand} ${props.vehicle.model}`}/>
              </Stack>
            </ListItem>
            <ListItem disablePadding>
              <Stack direction="row" alignItems="center" gap={1}>
                <Speed className="VehicleItem-content-info-icon"/>
                <ListItemText className="VehicleItem-content-info-text" primary={formatWithCommas(props.vehicle.mileage)}/>
              </Stack>
            </ListItem>
            <ListItem disablePadding>
              <Stack direction="row" alignItems="center" gap={1}>
                <Person className="VehicleItem-content-info-icon"/>
                <ListItemText className="VehicleItem-content-info-text" primary={formatIsPrivateDealer(props.vehicle.isPrivateDealer)}/>
              </Stack>
            </ListItem>
            <ListItem disablePadding>
              <Stack direction="row" alignItems="center" gap={1}>
                <Monitor className="VehicleItem-content-info-icon"/>
                <ListItemText className="VehicleItem-content-info-text" color={getColorFromWebsite(props.vehicle.website)} primary={props.vehicle.website}/>
              </Stack>
            </ListItem>
            <ListItem disablePadding>
              <Stack direction="row" alignItems="center" gap={1}>
                <AccessTime className="VehicleItem-content-info-icon"/>
                <ListItemText className="VehicleItem-content-info-text" primary={props.vehicle.dateScraped}/>
              </Stack>
            </ListItem>
          </List>
          <CustomButtonBorderless href={props.vehicle.link} size="small">View Ad</CustomButtonBorderless>
        </CardContent>
      </Card>
    </Box>
  );
}
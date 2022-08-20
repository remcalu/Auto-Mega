import { AppBar, Box, Link, Stack, Toolbar, Typography } from '@mui/material';
import "./SiteHeader.css"

export default function SiteHeader() {
  return (
    <Box className="SiteHeader">
      <AppBar position="static">
        <Toolbar>
          <Stack className="SiteHeader-body" direction="row" spacing={4}>
            <Stack direction="row" spacing={1}>
                <Typography fontWeight="bold" align="left" variant="h4" component="div">
                  Auto Mega
                </Typography>
            </Stack>
            <Stack direction="row" spacing={4}>
              <Link className="SiteHeader-button" rel="noopener noreferrer" href="https://www.autotrader.ca/">Autotrader</Link>
              <Link className="SiteHeader-button" rel="noopener noreferrer" href="https://www.carpages.ca/">Carpages</Link>
              <Link className="SiteHeader-button" rel="noopener noreferrer" href="https://www.kijiji.ca/b-cars-trucks/ontario/c174l9004">Kijiji</Link>
              <Link className="SiteHeader-button" rel="noopener noreferrer" href="https://remcalu.github.io/Personal-Website/#/">Site Creator</Link>
            </Stack>
          </Stack>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
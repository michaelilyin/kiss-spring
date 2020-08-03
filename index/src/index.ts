import express from "express";
import {listEventsBatch} from './calendar';
import {DateTime} from 'luxon';

const app = express();
const port = 18082;

app.get("/index-api/events", async (req, res) => {
  const now = DateTime.local();
  const timeMin = req.params.timeMin ?? now.startOf('day').toISO();
  const timeMax = req.params.timeMax ?? now.plus({day: 6}).endOf('day').toISO();
  const events = await listEventsBatch([
    'michael.s.ilyin@gmail.com',
    'rcq9te03ia04ip3os368iuvplo@group.calendar.google.com'
  ], {
    timeMin,
    timeMax
  });

  res.json({
    items: events
  });
});

app.listen(port, () => {
  // tslint:disable-next-line:no-console
  console.log(`server started at http://localhost:${port}`);
});

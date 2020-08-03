import {calendar_v3, google} from 'googleapis';
import _ from 'lodash';

export interface CalendarFilter {
  timeMin: string;
  timeMax: string;
}

export type Event = calendar_v3.Schema$Event;

const GAPI = process.env.GAPI_TOKEN;
if ((GAPI?.length ?? 0) === 0) {
  throw Error("GAPI token must be defined")
}

export async function listEvents(
  calendarId: string,
  filter: CalendarFilter
): Promise<Event[]> {
  return new Promise<Event[]>((resolve, reject) => {
    const calendar = google.calendar({version: 'v3', auth: GAPI});
    calendar.events.list({
      calendarId,
      timeMin: filter.timeMin,
      timeMax: filter.timeMax,
      maxResults: 1000,
      singleEvents: true,
      orderBy: 'startTime',
    }, (err, res) => {
      if (err) {
        reject(err);
      }
      const events = res?.data.items ?? [];
      resolve(events);
    });
  });
}

export async function listEventsBatch(
  calendarIds: string[],
  filter: CalendarFilter
): Promise<Event[]> {
  const requests = calendarIds.map(id => listEvents(id, filter));

  return Promise.all(requests).then(
    (results) => {
      return _.flatMap(results, r => r)
    }
  );
}

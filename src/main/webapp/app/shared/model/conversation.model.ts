import { type IUser } from '@/shared/model/user.model';
import { type IActivity } from '@/shared/model/activity.model';
import { type IMessage } from '@/shared/model/message.model';

export interface IConversation {
  id?: number;
  name?: string | null;
  color?: string | null;
  users?: IUser[] | null;
  activities?: IActivity[] | null;
  message?: IMessage | null;
}

export class Conversation implements IConversation {
  constructor(
    public id?: number,
    public name?: string | null,
    public color?: string | null,
    public users?: IUser[] | null,
    public activities?: IActivity[] | null,
    public message?: IMessage | null,
  ) {}
}

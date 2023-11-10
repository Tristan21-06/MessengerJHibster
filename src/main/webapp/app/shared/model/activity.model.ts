import { type IConversation } from '@/shared/model/conversation.model';

export interface IActivity {
  id?: number;
  imageActivity?: string | null;
  conversations?: IConversation[] | null;
}

export class Activity implements IActivity {
  constructor(
    public id?: number,
    public imageActivity?: string | null,
    public conversations?: IConversation[] | null,
  ) {}
}

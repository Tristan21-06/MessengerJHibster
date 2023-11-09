import { type IConversation } from '@/shared/model/conversation.model';

export interface IActivity {
  id?: number;
  imageAcivity?: string | null;
  conversations?: IConversation[] | null;
}

export class Activity implements IActivity {
  constructor(
    public id?: number,
    public imageAcivity?: string | null,
    public conversations?: IConversation[] | null,
  ) {}
}

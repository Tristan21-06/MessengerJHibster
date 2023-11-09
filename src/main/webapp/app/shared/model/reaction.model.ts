import { type IMessage } from '@/shared/model/message.model';

import { type ReactionType } from '@/shared/model/enumerations/reaction-type.model';
export interface IReaction {
  id?: number;
  type?: keyof typeof ReactionType | null;
  message?: IMessage | null;
}

export class Reaction implements IReaction {
  constructor(
    public id?: number,
    public type?: keyof typeof ReactionType | null,
    public message?: IMessage | null,
  ) {}
}

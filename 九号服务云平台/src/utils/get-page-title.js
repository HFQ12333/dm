import defaultSettings from '@/settings'

const title = defaultSettings.title || '九号便利云平台'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
